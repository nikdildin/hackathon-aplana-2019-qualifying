# hackathon-aplana-2019-qualifying
hackathon-aplana-2019-qualifying

Разработка
----------

important for development purposes in IntelliJ IDEA:
1. install Lombok support plugin
2. set Settings > Build, Execution, Deployment > Compiler > Annotation Processors > Enable annotation processing

build command line:
mvn clean install

run command line:
mvn clean install spring-boot:run


Установка без среды разработки
------------------------------

1. Установить Java Runtime Environment не менеее версии 8 (если не установлено) https://java.com/ru/download/
2. Скачать файл vingen.jar отсюда: https://github.com/nikdildin/hackathon-aplana-2019-qualifying/blob/master/release/vingen.jar (кнопка Download)
3. Запустить его кликнув 2 раза мышкой, или командой: java -jar vingen.jar
4. При запросе брандмауэра дать разрешение

Развернуть сервис на нестандартном порту можно командой:
java -jar vingen.jar --server.port=8888


Использование
-------------

Домашняя страница сервиса: http://localhost:8081

Генерация VIN без параметров: [GET] http://localhost:8081/create

Генерация VIN c параметрами: [POST] http://localhost:8081/create

в заголовке надо передать: Content-Type = application/json

в теле запроса надо передать: {	"year": 2015, "wmi": "ABC" }

year - год выпуска (должен быть больше 1900)

wmi - всемирный индекс изготовителя (должен содержать 3 знака, должен содержать только разрешенные символы)

допускается пропускать параметры


Настройка/просмотр БД
-------------
http://localhost:8081/h2

Setting Name: Generic H2 (Embedded)

Driver Class: org.h2.Driver

JDBC URL: jdbc:h2:~/vingen

User Name: sa

Password: