
<img src="https://capsule-render.vercel.app/api?type=waving&color=auto&height=200&section=header&text=JWT-practice&fontSize=90" />


# JWT ( JSON Web Token )

JWT는 유저를 인증하고 식별하기 위한 토큰(Token)기반 인증입니다.

토큰은 세션과 달리 서버가 아닌 클라이언트에 저장되기 때문에 메모리나 스토리지 등을 통해 세션을 관리했던 서버의 부담을 덜 수 있습니다.

데이터가 많이지면 토큰이 커질 수 있으며 한 번 발급된 이후 사용자의 정보를 바꾸어도 재발급하지 않는 이상 반영되지 안습니다.



## JWT 진행 순서

1. 클라이언트 사용자가 아이디, 패스워드를 통해 웹서비스를 인증합니다.

2. 서버에 서명된 JWT를 생성하여 클라이언트에 응답으로 돌려줍니다.

3. 클라이언트가 서버에 데이터를 추가적으로 요구할 때 JWT를 HTTP Header에 첨부합니다.

4. 서버에서 클라이언트로부터 온 JWT를 검증합니다.
