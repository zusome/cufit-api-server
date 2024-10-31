import boto3
import json

def get_ec2_instance_price(region, instance_type):
    # Boto3 Pricing 클라이언트 생성 (us-east-1로 고정, Pricing API는 일부 리전에서만 제공됨)
    pricing = boto3.client('pricing', region_name='us-east-1')

    try:
        # Pricing API로 인스턴스 가격 검색
        response = pricing.get_products(
            ServiceCode='AmazonEC2',
            Filters=[
                {'Type': 'TERM_MATCH', 'Field': 'instanceType', 'Value': instance_type},
                {'Type': 'TERM_MATCH', 'Field': 'location', 'Value': region},
                {'Type': 'TERM_MATCH', 'Field': 'operatingSystem', 'Value': 'Linux'},
                {'Type': 'TERM_MATCH', 'Field': 'preInstalledSw', 'Value': 'NA'},
                {'Type': 'TERM_MATCH', 'Field': 'tenancy', 'Value': 'Shared'},
                {'Type': 'TERM_MATCH', 'Field': 'capacitystatus', 'Value': 'Used'}
            ],
            MaxResults=1
        )
        # JSON으로 응답 파싱
        if response['PriceList']:
            product = json.loads(response['PriceList'][0])
        else: return "해당 인스턴스가 존재하지 않습니다"
        # 가격 정보 추출

        price_dimensions = product['terms']['OnDemand']
        for _, term_data in price_dimensions.items():
            for _, price_dimension in term_data['priceDimensions'].items():
                price_per_hour = price_dimension['pricePerUnit']['USD']
                return float(price_per_hour)

    except Exception as e:
        print(f"Error fetching EC2 instance price: {e}")
        return None
