# Variables para recursos existentes
variable "vpc_id" {}
variable "subnet_ids" { type = list(string) }
variable "security_group_id" {}
variable "alb_arn" {}

variable "alb_listener_arn" {
  description = "ARN del listener del ALB. Si no se provee, se usará el creado por Terraform."
  default     = null
}

variable "execution_role_arn" {
  description = "ARN del rol de ejecución de ECS ya existente."
  type        = string
}

variable "target_group_arn" {
  description = "ARN del target group existente para el servicio."
  type        = string
}

variable "ecs_cluster_arn" {
  description = "ARN o nombre del ECS cluster existente."
  type        = string
}

variable "log_group_name" {
  description = "Nombre del log group de CloudWatch existente."
  type        = string
}

# Task Definition
resource "aws_ecs_task_definition" "user_service" {
  family                   = "user-service-task"
  network_mode             = "awsvpc"
  requires_compatibilities = ["FARGATE"]
  cpu                      = "512"
  memory                   = "1024"
  execution_role_arn       = var.execution_role_arn
  container_definitions    = jsonencode([
    {
      name      = "user-service"
      image     = var.ecr_image_url
      portMappings = [{ containerPort = 8084, hostPort = 8084 }]
      environment = [
        { name = "DB_JDBC_URL", value = var.db_jdbc_url },
        { name = "DB_HOST", value = var.db_host },
        { name = "DB_USERNAME", value = var.db_username },
        { name = "DB_PASSWORD", value = var.db_password },
        { name = "JWT_SECRET", value = var.jwt_secret }
      ]
      logConfiguration = {
        logDriver = "awslogs"
        options = {
          awslogs-group         = var.log_group_name
          awslogs-region        = "us-east-1"
          awslogs-stream-prefix = "ecs"
        }
      }
    }
  ])
}


# Listener Rule para redirección
resource "aws_lb_listener_rule" "user_service" {
  listener_arn = var.alb_listener_arn
  priority     = 100
  action {
    type             = "forward"
    target_group_arn = var.target_group_arn
  }
  condition {
    path_pattern {
      values = ["/api/users/*", "/api/clients/*","/actuator/health"]
    }
  }
}

# Recurso para el servicio ECS
resource "aws_ecs_service" "user_service" {
  name            = "user-service"
  cluster         = var.ecs_cluster_arn
  task_definition = aws_ecs_task_definition.user_service.arn
  launch_type     = "FARGATE"
  desired_count   = 1
  network_configuration {
    subnets          = var.subnet_ids
    security_groups  = [var.security_group_id]
    assign_public_ip = true
  }
  load_balancer {
    target_group_arn = var.target_group_arn
    container_name   = "user-service"
    container_port   = 8084
  }
  depends_on = [aws_lb_listener_rule.user_service]
}

# Variable para la imagen de ECR
variable "ecr_image_url" {}

variable "db_jdbc_url" {}
variable "db_host" {}
variable "db_username" {}
variable "db_password" {}
variable "jwt_secret" {}
