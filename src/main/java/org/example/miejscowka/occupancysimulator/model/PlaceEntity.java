package org.example.miejscowka.occupancysimulator.model;

import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity
@Table(name = "PLACE")
public class PlaceEntity extends AbstractApplicationPersistenceEntity {

    @NotNull
    @Column(name = "NAME", nullable = false)
    private String name;

    @NotNull
    @Column(name = "CAPACITY", nullable = false)
    private Integer capacity;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "STREET")
    private String street;

    @Column(name = "BUILDING_NUMBER")
    private String buildingNumber;

    @Column(name = "APARTMENT_NUMBER")
    private String apartmentNumber;

//    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
//    @JoinTable(
//            name = "FAVOURIE_PLACE",
//            inverseJoinColumns = {@JoinColumn(
//                    name = "USER_ID", nullable = false, updatable = false
//            )},
//            joinColumns = {@JoinColumn(
//                    name = "PLACE_ID", nullable = false, updatable = false
//            )}
//    )
//    private Set<UserEntity> users = new HashSet<>();


    @OneToOne(mappedBy = "place", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private OpeningHoursEntity openingHours;

//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "place", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<PlaceImageEntity> placeImages = new ArrayList<>();

//    @NotNull
//    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
//    @JoinColumn(name = "CATEGORY_ID", nullable = false, referencedColumnName = "id")
//    private CategoryEntity category;

//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "place", cascade = CascadeType.MERGE, orphanRemoval = true)
//    private List<OccupancyEntity> occupancies = new ArrayList<>();

    public PlaceEntity() {

    }

    public PlaceEntity(String name, Integer capacity, String description, String street, String buildingNumber, String apartmentNumber, OpeningHoursEntity openingHours) {
        this.name = name;
        this.capacity = capacity;
        this.description = description;
        this.street = street;
        this.buildingNumber = buildingNumber;
        this.apartmentNumber = apartmentNumber;
//        this.users = users;
        this.openingHours = openingHours;
//        this.placeImages = placeImages;
//        this.category = category;
//        this.occupancies = occupancies;
    }

//    public void addPlaceImage(PlaceImageEntity placeImageEntity){
//        placeImages.add(placeImageEntity);
//        placeImageEntity.setPlace(this);
//    }
//
//    public void removePlaceImage(PlaceImageEntity placeImageEntity){
//        placeImages.remove(placeImageEntity);
//        placeImageEntity.setPlace(null);
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getBuildingNumber() {
        return buildingNumber;
    }

    public void setBuildingNumber(String buildingNumber) {
        this.buildingNumber = buildingNumber;
    }

    public String getApartmentNumber() {
        return apartmentNumber;
    }

    public void setApartmentNumber(String apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }

//    public Set<UserEntity> getUsers() {
//        return users;
//    }
//
//    public void setUsers(Set<UserEntity> users) {
//        this.users = users;
//    }

    public OpeningHoursEntity getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(OpeningHoursEntity openingHours) {
        this.openingHours = openingHours;
    }

}
