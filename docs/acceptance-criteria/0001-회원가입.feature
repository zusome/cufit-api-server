Feature: 소셜 로그인 기능
  Scenario: 사용자가 Apple 소셜 회원가입을 한다.
    Given 사용자가 Apple 게정을 가지고 있고
    When OIDC 와 함께 로그인 요청 하면
    Then 사용자는 인증 토큰을 응답 받는다

  Scenario: 사용자가 Apple 소셜 로그인을 한다.
    Given 사용자가 Apple 게정을 가지고 있고
    Given 사용자가 이미 회원가입을 완료했고
    When OIDC 와 함께 로그인 요청 하면
    Then 사용자는 인증 토큰을 응답 받는다.
