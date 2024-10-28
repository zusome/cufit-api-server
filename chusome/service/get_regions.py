import boto3

def get_regions():
    # boto3를 사용하여 리전 목록 가져오기
    pricing_client = boto3.client('pricing', region_name='us-east-1')

    # 리전 이름을 가져오기
    response = pricing_client.get_attribute_values(
        ServiceCode='AmazonEC2',
        AttributeName='location'
    )

    # 리전 이름 리스트 추출
    
    return [item['Value'] for item in response['AttributeValues']]
