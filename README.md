Этот проект представляет собой микросервисную архитектуру с двумя основными сервисами:

## Структура проекта

```
library-service/
├── book-service/          # Сервис управления книгами (порт 8080)
├── account-service/       # Сервис управления аккаунтами (порт 8081)
└── pom.xml                # Родительский Maven проект
```

## Сервисы

### Book Service (порт 8080)

- Управление книгами (создание, поиск, аренда, возврат)
- Интеграция с Account Service через Feign Client
- База данных: `bookdb`

### Account Service (порт 8081)

- Управление пользователями (создание, получение, удаление)
- База данных: `accountdb`

## API Endpoints

### Book Service (http://localhost:8080)

- `GET /api/books` - Получить все книги
- `GET /api/books/{id}` - Получить книгу по ID
- `POST /api/books` - Создать новую книгу
- `POST /api/books/{id}/borrow` - Взять книгу в аренду
- `POST /api/books/{id}/return` - Вернуть книгу
- `GET /api/books/available` - Получить доступные книги
- `GET /api/books/borrowed` - Получить арендованные книги
- `GET /api/books/user/{userId}` - Получить книги пользователя
- `GET /api/books/search?query=...` - Поиск книг

### Account Service (http://localhost:8081)

- `POST /api/accounts` - Создать новый аккаунт
- `GET /api/accounts/{id}` - Получить аккаунт по ID
- `DELETE /api/accounts/{id}` - Удалить аккаунт

## Примеры использования

### Создание аккаунта

```bash
curl -X POST http://localhost:8081/api/accounts \
  -H "Content-Type: application/json" \
  -d '{"name":"Иван","surname":"Иванов","age":25}'
```

### Создание книги

```bash
curl -X POST http://localhost:8080/api/books \
  -H "Content-Type: application/json" \
  -d '{"title":"Война и мир","author":"Лев Толстой"}'
```

### Аренда книги

```bash
curl -X POST http://localhost:8080/api/books/1/borrow \
  -H "Content-Type: application/json" \
  -d '{"userId":1}'
```