package practice.itunesfavorites;
import java.io.Serializable;

/**
 * Created by ojasv on 2/25/2017.
 */

public class Apps implements Serializable {
    String name, price, imgUrl;
    boolean isFavorite;
    @Override
    public String toString() {
        return "Apps{" +
                "name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                '}';
    }

    public Apps(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Apps(String name, String price, String imgUrl, boolean isFavorite) {
        this.name = name;
        this.price = price;
        this.imgUrl = imgUrl;
    }
}