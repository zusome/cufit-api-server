import json
from persistant.sqlite_db_manager import save_search_history, fetch_search_history
from service.get_regions import get_regions
from service.search_price import get_ec2_instance_price


def lambda_handler(event, context):
    headers = {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Methods': 'GET, POST, PUT, DELETE, OPTIONS',
            'Access-Control-Allow-Headers': 'Content-Type',
            }
    # 경로별 분기 처리
    # CORS preflight 요청에 대한 처리
    if event['requestContext']['http']['method'] == 'OPTIONS':
        return {
            'statusCode': 200,
            'headers': {
                'Access-Control-Allow-Headers': 'Content-Type, Authorization',
                'Access-Control-Allow-Origin': '*',  # 필요한 경우 특정 도메인으로 제한 가능
                'Access-Control-Allow-Methods': 'GET, POST, OPTIONS'
            },
            'body': ''
        }
    path = event['requestContext']['http']['path']
    if path == '/getRegions':
        # AWS 리전 정보 가져오기
        regions = get_regions()
        return {
            'statusCode': 200,
            'body': {"regions": regions},
            'headers': headers
        }

    if path == '/getPrice':
        # 가격정보 가져오기
        body = json.loads(event['body'])
    
        price = get_ec2_instance_price(body["region"], body["instanceType"])
        return {
            'statusCode': 200,
            'body': {"price": price},
            'headers': headers
        }

            
    if path == '/saveHistory':
        # 검색 기록을 저장
        body = json.loads(event['body'])
        instance_type = body.get('instanceType')
        region = body.get('region')
        price = body.get('price')
        
        # 필수 값 검증
        if not instance_type or not region or not price:
            return {
                'statusCode': 400,
                'body': json.dumps({'message': '인스턴스타입, 리전, 가격 정보 다시 확인'}),
                 'headers': headers
            }

        # 검색 기록 저장
        save_search_history(instance_type, region, price)

        return {
            'statusCode': 200,
            'body': json.dumps({'message': '검색이력 저장완료'}),
            'headers':headers
            
        }

    elif path == '/searchHistory':
        # 검색 기록을 조회
        search_history = fetch_search_history()
        # 검색 기록 데이터를 json으로 파싱작업
        body = []
        for row in search_history:
            tmp_dict  = {
                "instanceType": row[0],
                "region": row[1],
                "price": row[2],
                "date": row[3]
            }
            body.append(tmp_dict)
            
        return {
            'statusCode': 200,
            'body': body,
            'headers':headers
        }

    else:
        # 정의되지 않은 경로 처리
        return {
            'statusCode': 404,
            'body': json.dumps({'message': 'Not Found'}),
            'headers': headers
        }