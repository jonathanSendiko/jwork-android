package jonathan.jwork_android;

/**
 * Class untuk objek Job
 * @author Jonathan
 * @version 1.0
 * @since 10 Juni
 *
 */
public class Job {
    private int id;
    private String name;
    private Recruiter recruiter;
    private int fee;
    private String category;

    /**
     * Constructor Job
     * @param id
     * @param name
     * @param recruiter
     * @param fee
     * @param category
     */
    public Job(int id, String name, Recruiter recruiter, int fee, String category){
        this.id = id;
        this.name = name;
        this.recruiter = recruiter;
        this.fee = fee;
        this.category = category;
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

    public Recruiter getRecruiter() {
        return recruiter;
    }

    public void setRecruiter(Recruiter recruiter) {
        this.recruiter = recruiter;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
