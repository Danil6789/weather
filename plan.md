# Weather Viewer - План разработки

## 🎯 Milestone 1: Инфраструктура и настройка проекта

### 1.1 Базовая структура и зависимости
- [✅] Создать новый Gradle проект
- [✅] Добавить зависимости в build.gradle:
  - Spring Web MVC
  - Spring ORM (для Hibernate)
  - Hibernate Core
  - Thymeleaf (spring6)
  - Flyway Core
  - PostgreSQL Driver
  - Hibernate Validator
  - Lombok
  - Spring Security Crypto (только для BCrypt)
  - Servlet API (provided)
  - JUnit / Spring Test
  - H2 (для тестов)
- [✅] Настроить структуру пакетов:
  - config
  - controller
  - service
  - repository
  - entity
  - dto
  - exception
  - filter
  - util

### 1.2 Конфигурация приложения (Java Config, без XML)
- [✅] Создать WebAppInitializer:
  - Настроить DispatcherServlet
  - Зарегистрировать Spring контекст
- [✅] Создать AppConfig:
  - `@Configuration`
  - `@ComponentScan("org.example")`
  - `@EnableWebMvc`
- [✅] Создать DatabaseConfig:
  - `@EnableTransactionManagement`
  - DataSource (HikariCP)
  - SessionFactory (LocalSessionFactoryBean)
  - TransactionManager (HibernateTransactionManager)
- [ ] Создать ThymeleafConfig:
  - TemplateResolver
  - TemplateEngine
  - ViewResolver
- [✅] Создать SecurityConfig:
  - PasswordEncoder (BCrypt)

### 1.3 База данных и миграции
- [✅] Создать локальную базу данных PostgreSQL weather_db
- [✅] Настроить DataSource в DatabaseConfig (url, username, password)
- [ ] Создать тестовый профиль для интеграционных тестов (H2 in-memory)
- [✅] Flyway: Создать миграцию V1__create_users_table.sql
- [✅] Flyway: Создать миграцию V2__create_sessions_table.sql
- [✅] Flyway: Создать миграцию V3__create_locations_table.sql
- [✅] Создать FlywayConfig для запуска миграций при старте
- [ ] Собрать WAR и проверить, что таблицы создались при деплое

### 1.4 Модели данных (Entities)
- [✅] Создать Entity User (соответствует таблице Users)
- [✅] Создать Entity Session (связь Many-to-One с User, ID типа UUID)
- [✅] Создать Entity Location (связь Many-to-One с User)
- [ ] Создать репозитории (DAO) для всех Entity:
  - UserRepository (с методами: findById, findByLogin, save, update)
  - SessionRepository (с методами: save, delete, findById, deleteExpired)
  - LocationRepository (с методами: findByUserId, save, delete, findByIdAndUserId)
- [✅] Добавить BCryptPasswordEncoder в SecurityConfig

### 1.5 Интеграция с OpenWeather API
- [✅] Зарегистрироваться на OpenWeatherMap и получить API ключ
- [✅] Добавить ключ в отдельный config.properties или переменные окружения
- [✅] Создать DTO для ответов от OpenWeather API:
  - GeocodingResponse (для поиска локаций)
  - WeatherResponse (для получения погоды по координатам)
- [ ] Создать RestTemplate бин в AppConfig
- [✅] Поэкспериментировать с API вручную (через Insomnia/IDEA HTTP Client)

---

## 🎯 Milestone 2: Модуль аутентификации (Сессии и Cookies)

### 2.1 DTO и исключения для Auth
- [✅] Создать UserRegistrationDto (login, password, confirmPassword) с валидацией
- [✅] Создать UserLoginDto (login, password) с валидацией
- [] Создать кастомные исключения:
  - UserAlreadyExistsException
  - InvalidCredentialsException
  - UserNotFoundException
  - SessionExpiredException
- [ ] Создать GlobalExceptionHandler с `@ControllerAdvice`

### 2.2 Бизнес-логика Auth
- [✅] Создать UserService:
  - register() - проверка уникальности, хэширование пароля
  - findByLogin() - поиск пользователя
  - checkPassword() - проверка пароля
- [✅] Создать SessionService:
  - createSession() - создание сессии с UUID и expiresAt
  - deleteSession() - удаление сессии

### 2.3 Cookies и фильтры
- [✅] Создать CookieUtil для работы с cookies:
  - setSessionCookie() - установка cookie
  - deleteSessionCookie() - удаление cookie
  - getSessionIdFromCookie() - чтение cookie
  - **НЕ ИСПОЛЬЗОВАТЬ** стандартный JSESSIONID. Использовать SESSION_ID
- [✅] Создать SessionFilter (extends OncePerRequestFilter):
  - Читать cookie SESSION_ID из запроса
  - Валидировать сессию через SessionService
  - Класть объект User в request атрибуты
  - Пропускать публичные страницы (/login, /register, /css/*, /js/*)
- [✅] Зарегистрировать фильтр в WebAppInitializer или через FilterRegistrationBean

### 2.4 Контроллеры для Auth
- [✅] Создать AuthController:
  - GET /login — страница логина
  - GET /register — страница регистрации
  - POST /register — обработка регистрации
  - POST /login — обработка логина, создание сессии, установка cookie
  - POST /logout — удаление сессии, удаление cookie

### 2.5 Интеграционные тесты для Auth
- [✅] Настроить тестовый профиль с H2 in-memory
- [✅] Написать тесты для UserService:
  - Успешная регистрация
  - Регистрация с существующим логином -> исключение
- [✅] Написать тесты для SessionService:
  - Успешное создание сессии
  - Проверка истечения сессии

---

## 🎯 Milestone 3: Интеграция с OpenWeather API и бизнес-логика локаций

### 3.1 Сервис для работы с OpenWeather
- [✅] Создать OpenWeatherService:
  - searchLocations(String query) - поиск через Geocoding API
  - getWeather(double lat, double lon) - погода через Weather API
- [] Использовать RestTemplate (бин из AppConfig)
- [ ] Добавить обработку ошибок (4xx, 5xx) и исключение OpenWeatherApiException

### 3.2 Тестирование OpenWeather сервиса
- [ ] Создать мок для RestTemplate (MockRestServiceServer)
- [ ] Написать тесты:
  - Успешный поиск локаций (мок JSON ответа
  - Ошибка API -> OpenWeatherApiException

### 3.3 Бизнес-логика для локаций пользователя
- [✅] Создать LocationService:
  - addLocation() - добавление локации пользователю
  - getUserLocations() - список локаций пользователя
  - deleteLocation() - удаление с проверкой владельца
- [✅] Создать исключение LocationNotFoundException

---

## 🎯 Milestone 4: Контроллеры и Thymeleaf шаблоны (UI)

### 4.1 Подготовка фронтенда
- [✅] Скопировать статические ресурсы (CSS, картинки) в src/main/webapp/resources/static/
- [ ] Скопировать HTML макеты в src/main/webapp/WEB-INF/templates/
- [ ] Переименовать .html файлы (home.html, search.html, login.html, register.html)

### 4.2 Главная страница
- [ ] Создать HomeController:
  - GET /:
    - Получить User из request атрибута (установлен фильтром)
    - Если пользователь есть → загрузить его локации с погодой
    - Вернуть home.html с данными
- [ ] Адаптировать home.html под Thymeleaf

### 4.3 Страница поиска
- [ ] Создать SearchController:
  - GET /search:
    - Получить параметр query
    - Вызвать OpenWeatherService.searchLocations()
    - Вернуть search.html с результатами

### 4.4 Контроллеры для управления локациями
- [✅] Создать LocationController:
  - POST /locations/add — добавить локацию (редирект на главную)
  - POST /locations/delete/{id} — удалить локацию (редирект на главную)

---

## 🎯 Milestone 5: Финальная полировка и деплой

### 5.1 Обработка ошибок и валидация форм
- [ ] Улучшить GlobalExceptionHandler для кастомных страниц ошибок (404, 500)
- [ ] Добавить отображение ошибок валидации на страницах логина и регистрации
- [ ] Создать страницы ошибок в /webapp/WEB-INF/templates/error/

### 5.2 Профили конфигурации
- [ ] Создать отдельные конфигурационные классы для dev и prod профилей
- [ ] Использовать `@Profile` аннотации
- [ ] Вынести чувствительные данные (пароль БД, API ключ) в переменные окружения

### 5.3 Сборка и подготовка к деплою
- [ ] Настроить сборку WAR (плагин war в Gradle)
- [ ] Убедиться, что приложение собирается: `./gradlew clean build`
- [ ] WAR файл будет в `build/libs/weather-app.war`

### 5.4 Деплой на удаленный сервер
- [ ] Арендовать VPS (Timeweb, Selectel и т.д.)
- [ ] Установить на сервер: JRE, Tomcat, PostgreSQL
- [ ] Создать БД и пользователя БД
- [ ] Настроить Tomcat Manager для деплоя
- [ ] Задеплоить WAR-файл
- [ ] Настроить переменные окружения для продакшн профиля
- [ ] Проверить доступность приложения: `http://<server_ip>:8080/weather-app`

---

## 🎓 Что вы изучите в итоге

- ✅ Ручная настройка Spring
- ✅ Работа с WebApplicationInitializer
- ✅ Java Config вместо XML
- ✅ Ручная работа с сессиями и cookies (без Spring Security)
- ✅ Интеграция со сторонним REST API (OpenWeatherMap)
- ✅ Spring MVC с Thymeleaf (без Boot)
- ✅ Работа с БД через чистый Hibernate (SessionFactory, Session, Transactions)
- ✅ Создание DAO слоя без Spring Data JPA
- ✅ Миграции через Flyway
- ✅ Написание интеграционных тестов с H2
- ✅ Обработка исключений и `@ControllerAdvice`
- ✅ Деплой WAR на Tomcat