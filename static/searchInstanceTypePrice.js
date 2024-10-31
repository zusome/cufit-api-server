async function fetchRegions() {
    try {
        const response = await apiRequest('/getRegions');
        const regions = await response.regions;
        const selectBox = document.getElementById('region');
        
        // 셀렉트 박스 초기화
        selectBox.innerHTML = '';

        // 기본 옵션 추가
        const defaultOption = document.createElement('option');
        defaultOption.value = '';
        defaultOption.text = 'Select a region';
        selectBox.appendChild(defaultOption);

        // 리전 옵션 추가
        regions.forEach(region => {
            const option = document.createElement('option');
            option.value = region;
            option.text = region;
            selectBox.appendChild(option);
        });
    } catch (error) {
        console.error('Error fetching regions:', error);
    }
}

// 페이지가 로드될 때 리전 정보를 가져옴
window.onload = function() {
    fetchRegions();
};

document.getElementById('searchBtn').addEventListener('click', async function () {
    const region = document.getElementById('region').value;
    const instanceType = document.getElementById('instanceType').value;

    if (!instanceType) {
        alert("EC2 인스턴스 타입을 입력하세요");
        return;
    }
    body = {
        "region": region,
        "instanceType": instanceType
    }

    const response = await apiRequest('/getPrice',"POST",body);
    price = response.price
    // 가격 출력
    const resultDiv = document.getElementById('result');
    resultDiv.innerHTML = ` ${instanceType} 타입의 ${region}지역 시간당 가격: $${price}`;

    // 가격 출력 후  결과 저장 
    await saveSearchHistory(region, instanceType, price);
});

document.getElementById('historyBtn').addEventListener('click', async function () {
    // 검색이력 조회
    const history = await fetchSearchHistory();

    // 결과를 테이블로 변경
    const historyTable = document.getElementById('historyTable');
    const tbody = historyTable.querySelector('tbody');
    tbody.innerHTML = '';  // Clear any existing rows

    history.forEach(record => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${record.date}</td>
            <td>${record.instanceType}</td>
            <td>${record.region}</td>
            <td>$${record.price}</td>
        `;
        tbody.appendChild(row);
    });

    historyTable.style.display = 'table';
});

// 검색 이력 저장 함수
async function saveSearchHistory(region, instanceType, price) {
    body = {
        "region": region,
        "instanceType": instanceType,
        "price": price
    }
    return new Promise((resolve) => {
        apiRequest('/saveHistory','POST',body);
        console.log(`Saving to DB: ${region}, ${instanceType}, $${price}`);
        resolve();
    });
}

// 검색이력 조회 함수
async function fetchSearchHistory() {
    return new Promise((resolve) => {
        const history = apiRequest('/searchHistory')
        resolve(history);
    });
}
// 공통 request 함수
async function apiRequest(url, method = 'GET', body = null) {
    try {
        const request_url = "https://6xc4tvcdgut4qtb6onvbw3xhei0ahwro.lambda-url.ap-northeast-2.on.aws" + url
        const options = {
            method: method,
            headers: {
                'Content-Type': 'application/json'
            }
        };

        if (body) {
            options.body = JSON.stringify(body);
        }

        const response = await fetch(request_url, options);
        
        if (!response.ok) {
            throw new Error(`Error: ${response.status} - ${response.statusText}`);
        }

        return await response.json();  // JSON 형식으로 응답 처리
    } catch (error) {
        console.error('API 요청 중 에러가 발생했습니다:', error);
        throw error;
    }
}