package org.top.authorscatalog.form;

// BookFilterForm - форма для фильтрации товаров
public class BookFilterForm {

    private String book; // поле для поиска по паттерну для title/description
    private Integer minPrice;
    private Integer maxPrice;

    // конструктор
    public BookFilterForm() {
        book = "";
        minPrice = 0;
        maxPrice = 0;
    }

    public boolean isFormEmpty() {
        return book.equals("") && minPrice == 0 && maxPrice == 0;
    }

    // геттеры и сеттеры

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public Integer getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Integer minPrice) {
        this.minPrice = minPrice;
    }

    public Integer getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Integer maxPrice) {
        this.maxPrice = maxPrice;
    }
}
