variable "aws_region" {
  description = "La région AWS"
  type        = string
  default     = "us-east-1"
}

variable "cluster_name" {
  description = "Nom du cluster EKS"
  type        = string
  default     = "mykubernetes"
}

variable "subnet_ids" {
  description = "IDs des sous-réseaux pour le node group"
  type        = list(string)
  default     = [
    "subnet-02638865aedfa978a",  # us-east-1f
    "subnet-076608cfc34c1f733",  # us-east-1d
    "subnet-01230077daf93a3c6",  # us-east-1c
    "subnet-0e9d49e4f247fa83d"   # us-east-1b
  ]
}

variable "role_arn" {
  description = "ARN du rôle IAM pour EKS"
  type        = string
  default     = "arn:aws:iam::005777537952:role/LabRole"
}

variable "vpc_id" {
  description = "L'ID du VPC pour le cluster EKS"
  type        = string
  default     = "vpc-0b5bf1513c792fc9b"
}

variable "vpc_cidr" {
  description = "CIDR block for the VPC"
  type        = string
  default     = "10.0.0.0/16"
}