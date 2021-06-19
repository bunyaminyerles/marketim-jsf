package images;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;



public class ImagesView {

	
    private List<String> images;

    @PostConstruct
    public void init() {
        images = new ArrayList<String>();
        for (int i = 1; i <= 4; i++) {
            images.add("smile" + i + ".png");
        }
    }

    public List<String> getImages() {
        return images;
    }
}