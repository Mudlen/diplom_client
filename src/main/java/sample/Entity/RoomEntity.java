package sample.Entity;

import java.util.Objects;

public class RoomEntity {
    private int id;
    private String name;
    private int adminId;
    private int isPublic;

    public RoomEntity(){

    }
    public RoomEntity(int id, String name, int adminId, int isPublic){
        this.id = id;
        this.name = name;
        this.adminId = adminId;
        this.isPublic = isPublic;
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

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public int getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(int isPublic) {
        this.isPublic = isPublic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomEntity that = (RoomEntity) o;
        return id == that.id && adminId == that.adminId && isPublic == that.isPublic && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, adminId, isPublic);
    }

    @Override
    public String toString() {
        return "RoomEntity{" +
                "id=" + id +
                ", name='" + name +
                ", adminId=" + adminId +
                ", isPublic=" + isPublic +
                '}';
    }
}
