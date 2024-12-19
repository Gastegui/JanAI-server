class UserMacros:
    def __init__(self, recommended_calories, recommended_carbs, recommended_fats, recommended_fiber, recommended_proteins):
        self.consumed_calories = 0
        self.recommended_calories = recommended_calories
        self.consumed_carbs = 0
        self.recommended_carbs = recommended_carbs
        self.consumed_fats = 0
        self.recommended_fats = recommended_fats
        self.consumed_fiber = 0
        self.recommended_fiber = recommended_fiber
        self.consumed_proteins = 0
        self.recommended_proteins = recommended_proteins

    def __str__(self):
        return (f"Consumed Calories: {self.consumed_calories}; Recommended: {self.recommended_calories}\n"
                f"Consumed Protein: {self.consumed_proteins}g; Recommended: {self.recommended_proteins}\n"
                f"Consumed Carbs: {self.consumed_carbs}g; Recommended: {self.recommended_carbs}\n"
                f"Consumed Fiber: {self.consumed_fiber}g; Recommended: {self.recommended_fiber}\n"
                f"Consumed Fats: {self.consumed_fats}g; Recommended: {self.recommended_fats}")