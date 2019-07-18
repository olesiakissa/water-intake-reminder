package com.firebaseapp.waterintakereminder.model;

/**
 * Represents a user.
 *
 * @version 1.0
 */
public class User {

    private String uid;
    private String name;
    private String email;
    private String userpic;
    private int age;
    private float weightCurrent;
    private float weightGoal;
    private float waterIntakeGoal;
    /**
     * This value is required for water intake formula
     * to calculate the intake on days when user exercises
     * to keep the hydration level under control.
     */
    private float waterIntakeExerciseGoal;
    private float waterConsumed;
    private long exerciseDuration;
    private int streak;

    // TODO: Choose ds for storing values for statistics (waterConsumed and currentWeight)

    /**
     * Empty constructor that is required for Firebase's
     * automatic data mapping.
     */
    public User() {
    }

    public User(String uid, String name, String email) {
        this.uid = uid;
        this.name = name;
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserpic() {
        return userpic;
    }

    public void setUserpic(String userpic) {
        this.userpic = userpic;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public float getWeightCurrent() {
        return weightCurrent;
    }

    public void setWeightCurrent(float weightCurrent) {
        this.weightCurrent = weightCurrent;
    }

    public float getWeightGoal() {
        return weightGoal;
    }

    public void setWeightGoal(float weightGoal) {
        this.weightGoal = weightGoal;
    }

    public float getWaterIntakeGoal() {
        return waterIntakeGoal;
    }

    public void setWaterIntakeGoal(float waterIntakeGoal) {
        this.waterIntakeGoal = waterIntakeGoal;
    }

    public float getWaterIntakeExerciseGoal() {
        return waterIntakeExerciseGoal;
    }

    public void setWaterIntakeExerciseGoal(float waterIntakeExerciseGoal) {
        this.waterIntakeExerciseGoal = waterIntakeExerciseGoal;
    }

    public float getWaterConsumed() {
        return waterConsumed;
    }

    public void setWaterConsumed(float waterConsumed) {
        this.waterConsumed = waterConsumed;
    }

    public long getExerciseDuration() {
        return exerciseDuration;
    }

    public void setExerciseDuration(long exerciseDuration) {
        this.exerciseDuration = exerciseDuration;
    }

    public int getStreak() {
        return streak;
    }

    public void setStreak(int streak) {
        this.streak = streak;
    }

}
