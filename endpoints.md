### Endpoints
- Sign up
    ```bash
    curl -i -X POST \
       -H "Content-Type:application/x-www-form-urlencoded" \
       -d "username={username}" \
       -d "password={password}" \
       -d "email={email}" \
       -d "phoneNumber={phone_number}" \
     'http://localhost:8080/user'
    ```

- login
    ```
    http://localhost:8080/login
    ```

- logout
    ```
    http://localhost:8080/logout
    ```

- get login user's profile
    ```bash
    curl -i -X GET \
     'http://localhost:8080/userinfos'
    ```

- get all users
    - only for ADMINISTRATOR
    ```bash
    curl -i -X GET \
     'http://localhost:8080/users'
    ```

- get a user
    - only for ADMINISTRATOR
    ```bash
    curl -i -X GET \
     'http://localhost:8080/users/{username}'
    ```

- create account for login user
    ```bash
    curl -i -X POST \
       -H "Content-Type:application/x-www-form-urlencoded" \
       'http://localhost:8080/account'
    ```

- get login user's accounts
    ```bash
    curl -i -X GET \
     'http://localhost:8080/account'
    ```

- update login user's profile
    ```bash
    curl -i -X PUT \
       -H "Content-Type:application/x-www-form-urlencoded" \
       -d "email={email}" \
       -d "phoneNumber={phone_number}" \
     'http://localhost:8080/userprofiles'
    ```

- make a deposit transaction for a given account and the amount of money
    ```bash
    curl -i -X POST \
       -H "Content-Type:application/x-www-form-urlencoded" \
       -d "accountNumber={account_number}" \
       -d "amount={amount}" \
     'http://localhost:8080/transaction/deposit'
    ```

- make a withdrawal transaction for a given account and the amount of money
    ```bash
    curl -i -X POST \
       -H "Content-Type:application/x-www-form-urlencoded" \
       -d "accountNumber={account_number}" \
       -d "amount={amount}" \
     'http://localhost:8080/transaction/withdraw'
    ```

- make a transfer transaction to transfer a given amount of money from one account to another
    ```bash
    curl -i -X POST \
       -H "Content-Type:application/x-www-form-urlencoded" \
       -d "fromAccountNumber={account_number}" \
       -d "toAccountNumber={account_number}" \
       -d "amount={amount}" \
     'http://localhost:8080/transaction/transfer'
    ```


