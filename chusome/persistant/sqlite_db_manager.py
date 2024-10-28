import sqlite3
import boto3

from datetime import datetime, timedelta
from config.constants import Constants

s3_client = boto3.client('s3')
constants = Constants()
def upload_db_to_s3():
    s3_client.upload_file(Bucket=constants.BUCKET_NAME, Key=constants.DB_FILE_NAME, Filename=constants.LOCAL_DB_PATH)

def download_db_from_s3():
    print(str(constants.BUCKET_NAME), str(constants.DB_FILE_NAME), str(constants.LOCAL_DB_PATH))
    s3_client.download_file(Bucket=constants.BUCKET_NAME, Key=constants.DB_FILE_NAME, Filename=constants.LOCAL_DB_PATH)

def create_table():
    """ 검색 기록 테이블 생성 """
    with sqlite3.connect(constants.LOCAL_DB_PATH) as conn:
        cursor = conn.cursor()
        cursor.execute('''CREATE TABLE IF NOT EXISTS search_history (
                            id INTEGER PRIMARY KEY AUTOINCREMENT,
                            instance_type TEXT,
                            region TEXT,
                            price REAL,
                            search_time TEXT
                        )''')
        conn.commit()

def save_search_history(instance_type, region, price):
    download_db_from_s3()
    """ 검색 기록 저장 """
    create_table()  # 테이블이 없으면 생성
    with sqlite3.connect(constants.LOCAL_DB_PATH) as conn:
        cursor = conn.cursor()
        
        search_time = datetime.now() + timedelta(hours=9)
        search_time = search_time.strftime('%Y-%m-%d %H:%M:%S')
        cursor.execute('''INSERT INTO search_history (instance_type, region, price, search_time)
                          VALUES (?, ?, ?, ?)''', (instance_type, region, price, search_time))
        conn.commit()
    upload_db_to_s3()
    
def fetch_search_history():
    download_db_from_s3()
    """ 검색 기록 조회 """
    create_table()  # 테이블이 없으면 생성
    with sqlite3.connect(constants.LOCAL_DB_PATH) as conn:
        cursor = conn.cursor()
        cursor.execute('''SELECT instance_type, region, price, search_time FROM search_history
                          ORDER BY search_time DESC
                          LIMIT 10''')
        results = cursor.fetchall()
        
    return results