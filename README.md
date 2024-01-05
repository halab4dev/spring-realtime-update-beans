## Update Spring Bean Real Time
This is an example of updating code of Spring project that not need to restart application

### Scenario & Explanation
1. Start application.


2. Call API `POST /login`.

The response below is not what you expected.
```text
AccountServiceImpl.getAccount()
LoginApiImpl.execute()
```

3. Assume that you want to change the code of 2 classes below: 

`com.github.halab4dev.api.LoginApiImpl`

`com.github.halab4dev.service.AccountServiceImpl`


4. Create 2 classes `com.github.halab4dev.api.LoginApiFixed` and  `com.github.halab4dev.service.AccountServiceFixed`
that contain new code.

Note that you have to create new class with different name.


5. Build source code to a jar file
```shell
mvn clean package
```


6. Call API `PUT /beans` with request data
```json
{
    "jarFilePath": "<ABSOLUTE_PATH_OF_PROJECT_FOLDER>/target/spring-realtime-update-beans-0.0.1-SNAPSHOT.jar",
    "beans": [
        {
            "beanName": "accountServiceImpl",
            "targetClassName": "com.github.halab4dev.service.AccountServiceFixed",
            "constructorParameterTypes": []
        },
        {
            "beanName": "loginApiImpl",
            "targetClassName": "com.github.halab4dev.api.LoginApiFixed",
            "constructorParameterTypes": [
                "com.github.halab4dev.service.AccountService",
                "com.github.halab4dev.service.TokenService"
            ]
        }
    ]
}
```

This API will load new code from `.class` files of new `.jar` file, create new class instances of them
and replace existed beans with these new instances.

Note that in request data, you have to make beans in order of dependencies. 
For example, `LoginApiFixed` class is depended on `AccountService` class, 
so you have to put `accountServiceImpl` before `loginApiImpl` bean.

7. Call API `POST /login` again. The response is changed
```text
AccountServiceFixed.getAccount()
TokenServiceImpl.createToken()
LoginApiFixed.execute()
```