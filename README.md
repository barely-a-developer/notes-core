# notes-core  
  
This is a project dedicated to saving content from various sources (social networks, mobile app, website, etc.), managing it (sorting, filtering, grouping, etc.) and sharing.  
  
There are several microservices at the moment:  
  
#### Core Service (this one)  
https://github.com/barely-a-developer/notes-core
- It is the central place to store the content in the form of units I call Notes.  
  
#### VK Bot Service
https://github.com/barely-a-developer/notes-vkbot
- As the name implies - it is an integration with a VK Community Bot. 
- Content sent to this bot is saved to the Core. 
- In case there is an attachment - the file is first saved to a File Storage service.

#### File Storage Service 
https://github.com/barely-a-developer/notes-filestorage

- Some of the content (e.g. images) will be stored here

#### Web UI Service
https://github.com/barely-a-developer/notes-webui

- For managing the saved content

---

Example request to Core Service with HTTPie for searching and pagination (all params are optional):  
```  
http get "localhost:8080/notes?page=0&size=2&sort=message,asc&sort=id,desc"  
```
---
Join the Discord server if you want to collaborate: https://discord.gg/VrWtyhA  