# eks 설정
module "eks" {
  source  = "terraform-aws-modules/eks/aws"
  version = "17.0.0"

  cluster_name = "llife-eks"
  cluster_version = 1.27
  subnets      = module.vpc.private_subnets

  tags = {
    Terraform = "true"
    Project   = "EKS"
  }

  vpc_id = module.vpc.vpc_id

  node_groups_defaults = {
    ami_type  = "AL2_x86_64"
    disk_size = 20
    instance_types = ["t3.micro"]
    capacity_type  = "ON_DEMAND"  #[ON_DEMAND, SPOT]
  }

# 관리자는 master node에 명령을 내리고, 실제 일은 data node가 진행
  node_groups = {
    master = {
      desired_capacity = 1
      max_capacity     = 3
      min_capacity     = 1
    }
    data = {
      desired_capacity = 3
      max_capacity     = 3
      min_capacity     = 3
    }
  }
}

