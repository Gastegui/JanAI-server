package pbl.janai;

public class UserMacros {
    private int consumedCalories;
    private int recommendedCalories;
    private int consumedProteins;
    private int recommendedProteins;
    private int consumedFats;
    private int recommendedFats;
    private int consumedFiber;
    private int recommendedFiber;
    private int consumedCarbs;
    private int recommendedCarbs;

    public UserMacros(int recommendedCalories, int recommendedCarbs, 
    int recommendedFats, int recommendedFiber, int recommendedProteins){
        this.consumedCalories = 0;
        this.recommendedCalories = recommendedCalories;
        this.consumedCarbs = 0;
        this.recommendedCarbs = recommendedCarbs;
        this.consumedFats = 0;
        this.recommendedFats = recommendedFats;
        this.consumedFiber = 0;
        this.recommendedFiber = recommendedFiber;
        this.consumedProteins = 0;
        this.recommendedProteins = recommendedProteins;
    }

    public int getConsumedCalories() {
        return consumedCalories;
    }

    public void setConsumedCalories(int consumedCalories) {
        this.consumedCalories = consumedCalories;
    }

    public int getRecommendedCalories() {
        return recommendedCalories;
    }

    public void setRecommendedCalories(int recommendedCalories) {
        this.recommendedCalories = recommendedCalories;
    }

    public int getConsumedProteins() {
        return consumedProteins;
    }

    public void setConsumedProteins(int consumedProteins) {
        this.consumedProteins = consumedProteins;
    }

    public int getRecommendedProteins() {
        return recommendedProteins;
    }

    public void setRecommendedProteins(int recommendedProteins) {
        this.recommendedProteins = recommendedProteins;
    }

    public int getConsumedFats() {
        return consumedFats;
    }

    public void setConsumedFats(int consumedFats) {
        this.consumedFats = consumedFats;
    }

    public int getRecommendedFats() {
        return recommendedFats;
    }

    public void setRecommendedFats(int recommendedFats) {
        this.recommendedFats = recommendedFats;
    }

    public int getConsumedFiber() {
        return consumedFiber;
    }

    public void setConsumedFiber(int consumedFiber) {
        this.consumedFiber = consumedFiber;
    }

    public int getRecommendedFiber() {
        return recommendedFiber;
    }

    public void setRecommendedFiber(int recommendedFiber) {
        this.recommendedFiber = recommendedFiber;
    }

    public int getConsumedCarbs() {
        return consumedCarbs;
    }

    public void setConsumedCarbs(int consumedCarbs) {
        this.consumedCarbs = consumedCarbs;
    }

    public int getRecommendedCarbs() {
        return recommendedCarbs;
    }

    public void setRecommendedCarbs(int recommendedCarbs) {
        this.recommendedCarbs = recommendedCarbs;
    }

    @Override
    public String toString() {
        return "Consumed Calories: " + getConsumedCalories() + "; Recommended: " + getRecommendedCalories() + "\n" +
        "Consumed Protein: " + getConsumedProteins() + "g; Recommended: " + getRecommendedProteins() + "\n" +
        "Consumed Carbs: " + getConsumedCarbs() + "g; Recommended: " + getRecommendedCarbs() + "\n" + 
        "Consumemd Fiber: " + getConsumedFiber() + "g; Recommended: " + getRecommendedFiber() + "\n" +
        "Consumed Fats: " + getConsumedFats() + "g; Recommended: " + getRecommendedFats();
    }

    
}
