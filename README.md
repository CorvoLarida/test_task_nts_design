# Test Task For Java Backend Dev In NTS Design

Клиентская часть представляет собой веб-страницы.
2 страницы для 2 заданий.

- 1 задание:\
   На странице присутствуют 2 кнопки: Выбрать файл и Загрузить файл.\
   Если название и тип файла соответствуют формату "DDMMYYYY_HHMMSS.format",
   файл сохраняется в файловую систему проекта (в папку files),
   а информация о файле сохраняется в БД.\
   Контроллер возвращает строковое текстовое сообщение о результате обработки файла, которое показывается пользователю.
- 2 задание:\
   Пользователь вводит псевдоним и подключается к серверу.\
   Пользователь может ввести 3 команды:
   - Комманда "Get File Information" (техническое название "fileInfo")
     возращает информация о файле на сервере. 
   - Комманда "Add Log" (техническое название "addLog"), при наличии текста лога,
     добавляет лог пользователя в БД.
   - Комманда "Display All Logs" (техническое название "logs")
     возвращает массив всех логов пользователя.

   Для ввода комманд есть 2 способа: 
    - форма, в которой выбирается комманда и добавляется необходимая информация для её выполнения.
    - текстовое поле, в которое вводится тело запроса в формате JSON.

   Контроллер возращает строковый результат выполнения команды, 
   который выводится в текстовое поле "Output".

Мои основные проблемы с заданием:
1) Трудности в представлении как должен выглядить итоговый результат.
