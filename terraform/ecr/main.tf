terraform {
  backend "s3" {
    bucket = "mi-bucket-terraform-estado"
    key    = "user-service/ecr/terraform.tfstate"
    region = "us-east-1"
  }
}

provider "aws" {
  region = "us-east-1"
}

resource "aws_ecr_repository" "user_service" {
  name                 = "user-service"
  image_tag_mutability = "MUTABLE"
  image_scanning_configuration {
    scan_on_push = true
  }
}

output "ecr_repository_url" {
  value = aws_ecr_repository.user_service.repository_url
}
