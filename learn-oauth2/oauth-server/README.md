# 认证地址

授权码模式: GET http://localhost:8080/oauth/authorize?client_id=javaboy&response_type=code&scope=all&redirect_uri=http://localhost:8081/index.html&state=abc
-->回调地址参数: http://localhost:8081/index.html?code=mJQIm5

简化模式: GET http://localhost:8080/oauth/authorize?client_id=implicit&response_type=token&scope=all&redirect_uri=http://localhost:8081/index.html
-->回调会将token传回: http://localhost:8081/index.html#access_token=8470caee-643c-4301-9613-ba3ef8c27636&token_type=bearer&expires_in=7199


# 获取TOKEN

授权码模式: POST http://localhost:8080/oauth/token?grant_type=authorization_code&code=mJQIm5&client_id=javaboy&client_secret=123&redirect_uri=http://localhost:8081/index.html&scope=all&state=hello
{
"access_token": "e4c1e9b6-bb9e-4a39-ba55-c8b15ed0caaf",
"token_type": "bearer",
"refresh_token": "b716e206-3d47-4cbf-91e1-d54e61a272c8",
"expires_in": 4972,
"scope": "all"
}

密码模式: POST http://localhost:8080/oauth/token?grant_type=password&client_id=u_pwd&client_secret=123&username=admin&password=123
{
"access_token": "dcd14ceb-eb62-49f5-9d46-beb1d327a3a1",
"token_type": "bearer",
"refresh_token": "a402a8d8-7c2d-43f2-8ede-5d258cb2b065",
"expires_in": 6866,
"scope": "all"
}

客户端模式: POST http://localhost:8080/oauth/token?grant_type=client_credentials&client_id=u_client&client_secret=123
{
"access_token": "913f1a28-3118-4773-bb5e-33765a44b383",
"token_type": "bearer",
"expires_in": 7199,
"scope": "all"
}