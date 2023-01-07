package com.example.hsqldb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
Створити CRUD REST-API, наприклад TODO-список (що/коли/статус)
Із авто-документацією і збереженням у ін-меморі/postgresql/mariadb
ToDo-задачі мають проходити стани Planned -> Work_in_progress -> Done, Cancelled Done і Cancelled
 - фінальні стани, напрям зміни стану - односторонній.
Використайте enum для стану, покрийте тестами логіку зміну стану.
Задеплоїти jar із API на власний ресурс AWS Elastic Beanstalk
Написати шелл-скрипти із викликами API за допомогою curl
Додати до АПІ пару користувачів із різними ролями та i18n
Додатково: повикликати сервіси інших студентів HttpClient.
Наприклад залогуйте get /hello.
Як є можливість щось більше - синхронізація даних між сервісами.
*/

@SpringBootApplication
public class HsqldbApplication {
    public static void main(String[] args) {
        SpringApplication.run(HsqldbApplication.class, args);
    }
}
