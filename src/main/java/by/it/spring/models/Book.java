package by.it.spring.models;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Objects;
@Entity
@Table(name = "Book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @NotEmpty(message = "Название книги не должно быть пустым")
    @Size(min = 2, max = 30, message = "Название книги должно быть от 2 до 30 символов длиной")
    @Column(name = "name")
    String name;
    @NotEmpty(message = "Автор не должен быть пустым")
    @Size(min = 2, max = 50, message = "Имя автора должно быть от 2 до 50 символов длиной")
    String author;
    @Min(value = 1800, message = "Год должен быть больше, чем 1500")
    @Column(name = "year")
    int year;
    @ManyToOne
    @JoinColumn(name = "person_id",referencedColumnName = "id")
    Person owner;
    @Column(name = "capture_date")
    @Temporal(TemporalType.TIMESTAMP)
    Date captureDate;
    @Transient
    boolean expired;

    public Book() {
    }

    public Date getCaptureDate() {
        return captureDate;
    }

    public void setCaptureDate(Date captureDate) {
        this.captureDate = captureDate;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }


    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return getId() == book.getId() && getYear() == book.getYear() && Objects.equals(getName(), book.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getYear());
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", year=" + year +
                '}';
    }
}
