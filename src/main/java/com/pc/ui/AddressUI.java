package com.pc.ui;

import com.pc.dao.EntityDAOFacade;
import com.pc.entities.Address;
import com.pc.framework.AbstractUI;
import com.pc.service.AddressService;
import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.primefaces.model.map.Circle;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component("addressUI")
@ViewScoped
public class AddressUI extends AbstractUI {

    @Autowired
    AddressService addressService;
    @Autowired
    EntityDAOFacade entityDAOFacade;
    private ArrayList<Address> addressList;
    private Address address;
    private LazyDataModel<Address> dataModel;
    private boolean showMap;
    private MapModel circleModel;

    @PostConstruct
    public void init() {
        address = new Address();
        loadAddressInfo();
    }

    public void saveAddress() {
        try {
            addressService.saveAddress(address);
            displayInfoMssg("Address added successful...!!");
            loadAddressInfo();
            reset();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public void deleteAddress() {
        try {
            addressService.deleteAddress(address);
            displayWarningMssg("Address deleted successful...!!");
            loadAddressInfo();
            reset();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public List<Address> findAllAddress() {
        List<Address> list = new ArrayList<>();
        try {
            list = addressService.findAllAddress();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    public Page<Address> findAllAddressPageable() {
        Pageable p = null;
        try {
            return addressService.findAllAddress(p);
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public List<Address> findAllAddressSort() {
        Sort s = null;
        List<Address> list = new ArrayList<>();
        try {
            list = addressService.findAllAddress(s);
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    public void reset() {
        logger.info("Resetting address...");
        address = new Address();
    }

    public void loadAddressInfo() {
        dataModel = new LazyDataModel<Address>() {

            private static final long serialVersionUID = 1L;
            private List<Address> list = new ArrayList<Address>();

            @Override
            public List<Address> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

                try {
                    list = (List<Address>) entityDAOFacade.getResultList(Address.class, first, pageSize, sortField, sortOrder, filters);
                    dataModel.setRowCount(entityDAOFacade.count(filters, Address.class));
                } catch (Exception e) {
                    logger.fatal(e);
                }
                return list;
            }

            @Override
            public Object getRowKey(Address obj) {
                return obj.getId();
            }

            @Override
            public Address getRowData(String rowKey) {
                for (Address obj : list) {
                    if (obj.getId().equals(Long.valueOf(rowKey)))
                        return obj;
                }
                return null;
            }

        };

    }

    public void onCircleSelect(OverlaySelectEvent event) {
        displayInfoMssg("Institution Location");
    }

    public void prepareMapModel() {
        try {
            circleModel = new DefaultMapModel();
            if (address.getLatitude() != null && address.getLongitude() != null) {
                showMap = true;
                LatLng coord1 = new LatLng(address.getLatitude(), address.getLongitude());
                // Circle
                Circle circle1 = new Circle(coord1, 500);
                circle1.setStrokeColor("#d93c3c");
                circle1.setFillColor("#d93c3c");
                circle1.setFillOpacity(0.5);
                circleModel.addOverlay(circle1);
            }
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public ArrayList<Address> getAddressList() {
        return addressList;
    }

    public void setAddressList(ArrayList<Address> addressList) {
        this.addressList = addressList;
    }

    public LazyDataModel<Address> getDataModel() {
        return dataModel;
    }

    public void setDataModel(LazyDataModel<Address> dataModel) {
        this.dataModel = dataModel;
    }

    public boolean isShowMap() {
        return showMap;
    }

    public void setShowMap(boolean showMap) {
        this.showMap = showMap;
    }

    public MapModel getCircleModel() {
        return circleModel;
    }

    public void setCircleModel(MapModel circleModel) {
        this.circleModel = circleModel;
    }
}
