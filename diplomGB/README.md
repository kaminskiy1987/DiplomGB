Справка:

1.EndPoints:

Все EndPoints доступны по адресу localhost:8080

2.Для всех:

Данные Endpoints используются для регистрации, логирования при вводе адреса localhost:8080,
вы будете автоматически переведены на страничку логина.
По итогу логирования вы будете перенаправлены на страничку получения токена, который
в дальнейшем используется в Postman для обращения к другим Endpoints.

1. @GetMapping("/login") - получение странички для логирования
2. @PostMapping("/login/log") - передача данных логина серверу.
3. @GetMapping("/registration") - получение странички для регистрации
4. @PostMapping("/registration/reg") - передача данных регистрации серверу.

3.Для администратора:

1. @GetMapping("/applications/admin/list") - получение списка всех заявок
2. @GetMapping("/applications/admin/list_by_status") - получение списка всех заявок по статусу. Принимает параметром *status (
   not_started; in_progress; complete)*.
3. @PutMapping("/applications/admin/{id}") - изменение статуса заявки по *id*. Принимает параметром новый *status (not_started;
   in_progress; complete)*, *id* берется из пути.
4. @DeleteMapping("/applications/admin/{id}") - удаление заявки по *id*. *id* берется из пути.
5. @DeleteMapping("/users/{id}") - удаление пользователя по *id*. *id* берется из пути.

4.Для пользователя:

1. @PostMapping("/applications/create") - создание заявки. Принимает параметрами *name* и *description*.
2. @GetMapping("/applications/user/list") - получение списка всех заявок данного пользователя.
3. @GetMapping("/applications/user/list_by_status") - получение списка всех заявок по статусу данного пользователя. Принимает
   параметром *status (not_started; in_progress; complete)*.
