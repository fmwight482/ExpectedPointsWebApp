provider "aws" {
  region = local.region
  shared_credentials_file = "~/.aws/credentials"
}

resource "aws_lambda_function" "down_series_football_game" {
  filename      = "../target/ExpectedPointsWebApp-${local.maven-version}.jar"
  function_name = "down_series_football_game"
  role          = aws_iam_role.iam_for_lambda.arn
  handler       = "DownSeriesFootballSim.DownSeriesGameHandler"
  // full execution takes ~180 seconds
  timeout       = 240
  memory_size   = 2048

  # The filebase64sha256() function is available in Terraform 0.11.12 and later
  source_code_hash = filebase64sha256("../target/ExpectedPointsWebApp-${local.maven-version}.jar")

  runtime = "java11"

  depends_on = [
    aws_iam_role_policy_attachment.lambda_logs,
    aws_cloudwatch_log_group.down_series_football_game,
  ]
}

resource "aws_lambda_permission" "apigw_lambda" {
  statement_id  = "AllowExecutionFromAPIGateway"
  action        = "lambda:InvokeFunction"
  function_name = aws_lambda_function.down_series_football_game.function_name
  principal     = "apigateway.amazonaws.com"

  source_arn = "arn:aws:execute-api:${local.region}:${local.accountId}:${aws_api_gateway_rest_api.expected_points_api.id}/*/${aws_api_gateway_method.method.http_method}${aws_api_gateway_resource.points.path}"
}

resource "aws_cloudwatch_log_group" "down_series_football_game" {
  name              = "/aws/lambda/down_series_football_game"
  retention_in_days = 14
}

resource "aws_s3_bucket" "webapp" {
  bucket = "expected-points-webapp"
  acl    = "public-read"
  policy = file("policy.json")

  website {
    index_document = "index.html"
    error_document = "error.html"

    routing_rules = <<EOF
[{
    "Condition": {
        "KeyPrefixEquals": "docs/"
    },
    "Redirect": {
        "ReplaceKeyPrefixWith": "documents/"
    }
}]
EOF
  }
}

resource "aws_s3_bucket_object" "index" {
  bucket = "expected-points-webapp"
  key = "index.html"
  source = "index.html"
}
