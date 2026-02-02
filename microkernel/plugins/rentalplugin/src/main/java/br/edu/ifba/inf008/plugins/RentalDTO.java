package br.edu.ifba.inf008.plugins;

public class RentalDTO {
        private final String email;
        private final String type_name;
        private final String vehicle_id;
        private final String start_date;
        private final String scheduled_end_date;
        private final String pickup_location;
        private final String base_rate;
        private final String insurance_fee;

        public RentalDTO(String email, String type_name, String vehicle_id, String start_date, String scheduled_end_date, String pickup_location, String base_rate, String insurance_fee){
            this.email = email;
            this.type_name = type_name;
            this.vehicle_id = vehicle_id;
            this.start_date = start_date;
            this.scheduled_end_date = scheduled_end_date;
            this.pickup_location = pickup_location;
            this.base_rate = base_rate;
            this.insurance_fee = insurance_fee;
        }

        public String getEmail() {return email;}

        public String getTypeName() {return type_name;}

        public String getVehicleId() {return vehicle_id;}

        public String getStartDate() {return start_date;}

        public String getScheduledEndDate() {return scheduled_end_date;}

        public String getPickupLocation() {return pickup_location;}

        public String getBaseRate() {return base_rate;}

        public String getInsuranceFee() {return insurance_fee;}

}
