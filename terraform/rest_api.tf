resource "aws_api_gateway_rest_api" "expected_points_api" {
  name = "expected_points_api"

  endpoint_configuration {
    types = ["REGIONAL"]
  }
}

resource "aws_api_gateway_resource" "points" {
  path_part   = "points"
  parent_id   = aws_api_gateway_rest_api.expected_points_api.root_resource_id
  rest_api_id = aws_api_gateway_rest_api.expected_points_api.id
}

resource "aws_api_gateway_method" "method" {
  rest_api_id   = aws_api_gateway_rest_api.expected_points_api.id
  resource_id   = aws_api_gateway_resource.points.id
  http_method   = "GET"
  authorization = "NONE"
}

resource "aws_api_gateway_integration" "integration" {
  rest_api_id             = aws_api_gateway_rest_api.expected_points_api.id
  resource_id             = aws_api_gateway_resource.points.id
  http_method             = aws_api_gateway_method.method.http_method
  integration_http_method = "POST"
  type                    = "AWS_PROXY"
  uri                     = aws_lambda_function.down_series_football_game.invoke_arn
}

resource "aws_api_gateway_deployment" "test_deployment" {
  depends_on = [aws_api_gateway_integration.integration]

  rest_api_id = aws_api_gateway_rest_api.expected_points_api.id
  stage_name  = "test"

  lifecycle {
    create_before_destroy = true
  }
}

resource "aws_api_gateway_stage" "test" {
  stage_name = "test"
  description = "stage for testing the API"

  rest_api_id = aws_api_gateway_rest_api.expected_points_api.id
  deployment_id = aws_api_gateway_deployment.test_deployment.id
}

resource "aws_api_gateway_method_settings" "general_settings" {
  rest_api_id = aws_api_gateway_rest_api.expected_points_api.id
  stage_name  = aws_api_gateway_deployment.test_deployment.stage_name
  method_path = "*/*"

  settings {
    # Enable CloudWatch logging and metrics
    metrics_enabled        = true
    data_trace_enabled     = true
    logging_level          = "INFO"

    # Limit the rate of calls to prevent abuse and unwanted charges
    throttling_rate_limit  = 100
    throttling_burst_limit = 50
  }
}
