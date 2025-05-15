package phi.ap.model.items.relations;

import phi.ap.model.Player;
import phi.ap.model.items.Item;
import phi.ap.model.items.products.Product;

public class MarriageRequest {

    Player applicant;
    Player respondent;
    Product ring;

    MarriageRequest(Player applicant, Player respondent, Product ring) {
        this.applicant = applicant;
        this.respondent = respondent;
        this.ring = ring;
    }
    public Player getApplicant() {
        return applicant;
    }

    public void setApplicant(Player applicant) {
        this.applicant = applicant;
    }

    public Player getRespondent() {
        return respondent;
    }

    public void setRespondent(Player respondent) {
        this.respondent = respondent;
    }

    public Product getRing() {
        return ring;
    }

    public void setRing(Product ring) {
        this.ring = ring;
    }

}
