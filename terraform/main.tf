provider "aws" {
  region = "us-east-2"
  shared_credentials_file = "~/.aws/credentials"
}

resource "aws_iam_role" "iam_for_lambda" {
  name = "iam_for_lambda"

  assume_role_policy = <<EOF
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Action": "sts:AssumeRole",
      "Principal": {
        "Service": "lambda.amazonaws.com"
      },
      "Effect": "Allow",
      "Sid": ""
    }
  ]
}
EOF
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

  environment {
    variables = {
      foo = "bar"
    }
  }
}