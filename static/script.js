document.addEventListener('DOMContentLoaded', () => {
    const loginForm = document.getElementById('loginForm');
    if (loginForm) {
        loginForm.addEventListener('submit', (e) => {
            e.preventDefault();
            const username = document.getElementById('username').value;
            const password = document.getElementById('password').value;
            console.log('로그인 시도:', username, password);
            // 여기에 실제 로그인 로직을 구현하세요
            alert('로그인 기능은 아직 구현되지 않았습니다.');
        });
    }
});
