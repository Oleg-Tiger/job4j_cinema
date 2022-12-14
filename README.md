job4j_cinema
===========

## Описание 
В данном проекте разработан сайт для покупки билетов в кинотеатр. При загрузке главной страницы нам предоставляется
возможность выбора фильма. После выбора, мы переходим на страницы выбора ряда, а затем места. После выбору места, пользователю
отображается информация о сеансе и возможность подтвердить покупку, либо вернуть на главную страницу. После подтверждения
покупки загружается информационный экран: либо покупка успешна, либо данное место уже куплено. Если покупка прошла, то билет
добавляется в базу данных. Если перед покупкой пользователь не авторизовался, то его перенаправит на страницу авторизации.
Данные о пользователях также хранятся в базе данных. На страницу авторизации можно перейти с любой из страниц сайта.

## Используемые технологии:
- JDK17
- Maven 3.8.1
- PostgreSQL 13.3
- Spring Boot 2.5.2
- Liquibase 3.6.2
- Bootstrap 4.4.1
- Thymeleaf 3.0.12
- JUnit 5
- H2 1.4.200
- Mockito 4.0.0

## Требуемое окружение:
- JDK17
- Maven 3.8.1
- PostgreSQL 13.3
- Браузер

## Запуск проекта:
- Создать БД cinema
```jdbc:postgresql://127.0.0.1:5432/cinema```
- Собрать jar файл с помощью команды
```mvn install```
- Запустить приложение с помощью собранного jar-файла, выполнив команду
```java -jar target/job4j_cinema-1.0.jar```
- Перейти по ссылке
```http://localhost:8080/sessions```

## Страницы проекта:

### Главная
![](img/Main.png)

### Выбор ряда
![](img/Row.png)

### Выбор места
![](img/Seat.png)

### Информация
![](img/Info.png)

### Место занято
![](img/Occupied.png)

### Покупка успешна
![](img/Success.png)

### Регистрация
![](img/Registration.png)

### Авторизация
![](img/Autorization.png)

## Контактная информация:
free_person@inbox.ru
Ершов Олег