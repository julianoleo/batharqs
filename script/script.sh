#!/bin/bash

aws s3 rb s3://bucket --endpoint-url=http://localhost:4566 --region sa-east-1
aws s3 mb s3://bucket --endpoint-url=http://localhost:4566 --region sa-east-1
aws s3 cp DADOS.TXT s3://bucket/DADOS/ --endpoint-url=http://localhost:4566 --region sa-east-1
aws s3 cp LOGS.TXT s3://bucket/LOGS/ --endpoint-url=http://localhost:4566 --region sa-east-1
aws s3 cp DADOS_TESTE.TXT s3://bucket/DADOS/ --endpoint-url=http://localhost:4566 --region sa-east-1
aws s3 cp DADOS_01.TXT s3://bucket/DADOS/ --endpoint-url=http://localhost:4566 --region sa-east-1
aws s3 cp DADOS_02.TXT s3://bucket/DADOS/ --endpoint-url=http://localhost:4566 --region sa-east-1
