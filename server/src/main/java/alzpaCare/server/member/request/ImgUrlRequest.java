package alzpaCare.server.member.request;

import jakarta.validation.constraints.Size;

public record ImgUrlRequest(

        @Size(max = 254)
        String imgUrl
        
) {
}
