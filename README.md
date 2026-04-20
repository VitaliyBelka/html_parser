# html_parser

A quick web scraper built at a friend's request because the target website doesn't provide a proper book list.

It extracts book titles and authors from `studyenglishwords.com` using Selenide and generates a simple static HTML page with the results.

## ⚠️ Fragile by Design

This is a one-off script. It relies on specific page structure and manual text corrections (the site's encoding is messy). It will likely break if the website layout changes.

## 🛠️ Tech Stack

- Java
- Selenide
- Edge WebDriver (easily swappable)
