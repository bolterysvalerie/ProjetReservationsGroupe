-- TABLE Artiste_Type
ALTER TABLE artiste_type
    ADD COLUMN artist_id BIGINT NOT NULL AFTER id,
    ADD COLUMN type_id BIGINT NOT NULL AFTER artist_id;

-- TABLE Locations
ALTER TABLE locations
    ADD COLUMN locality_id BIGINT NOT NULL AFTER id;

-- TABLE Artiste_Type_Show
ALTER TABLE artiste_type_show
    ADD COLUMN artist_type_id BIGINT NOT NULL AFTER id,
    ADD COLUMN show_id BIGINT NOT NULL AFTER artist_type_id;

-- TABLE Representation_Reservation
ALTER TABLE representation_reservation
    ADD COLUMN price_id BIGINT NOT NULL AFTER id,
    ADD COLUMN representation_id BIGINT NOT NULL AFTER price_id,
    ADD COLUMN reservation_id BIGINT NOT NULL AFTER representation_id;

-- TABLE Representations
ALTER TABLE representations
    ADD COLUMN location_id BIGINT NOT NULL AFTER id,
    ADD COLUMN show_id BIGINT NOT NULL AFTER location_id;

-- TABLE Shows
ALTER TABLE shows
    ADD COLUMN location_id BIGINT NOT NULL AFTER id;

-- TABLE Reservations
ALTER TABLE reservations
    ADD COLUMN user_id BIGINT NOT NULL AFTER id;

-- TABLE Role_User
ALTER TABLE role_user
    ADD COLUMN role_id BIGINT NOT NULL AFTER id,
    ADD COLUMN user_id BIGINT NOT NULL AFTER role_id;

-- TABLE Reviews
ALTER TABLE reviews
    ADD COLUMN show_id BIGINT NOT NULL AFTER id,
    ADD COLUMN user_id BIGINT NOT NULL AFTER show_id;

-- Add Constraints for Locations
ALTER TABLE locations
    ADD CONSTRAINT locations_localities UNIQUE (locality_id);

ALTER TABLE locations
    ADD CONSTRAINT locations_id UNIQUE (id);

-- Add Constraints for Artiste_Type
ALTER TABLE artiste_type
    ADD CONSTRAINT fk_artiste_type_type FOREIGN KEY (type_id)
        REFERENCES types (id) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE artiste_type
    ADD CONSTRAINT fk_artiste_type_artist FOREIGN KEY (artist_id)
        REFERENCES artists (id) ON UPDATE CASCADE ON DELETE CASCADE;

-- Add Constraints for Artiste_Type_Show
ALTER TABLE artiste_type_show
    ADD CONSTRAINT fk_artiste_type_show_artist_type FOREIGN KEY (artist_type_id)
        REFERENCES artiste_type (id) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE artiste_type_show
    ADD CONSTRAINT fk_artiste_type_show_show FOREIGN KEY (show_id)
        REFERENCES shows (id) ON UPDATE CASCADE ON DELETE CASCADE;

-- Add Constraints for Representation_Reservation
ALTER TABLE representation_reservation
    ADD CONSTRAINT fk_representation_reservation_representation FOREIGN KEY (representation_id)
        REFERENCES representations (id) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE representation_reservation
    ADD CONSTRAINT fk_representation_price FOREIGN KEY (price_id)
        REFERENCES prices (id) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE representation_reservation
    ADD CONSTRAINT fk_representation_reservation FOREIGN KEY (reservation_id)
        REFERENCES reservations (id) ON UPDATE CASCADE ON DELETE CASCADE;

-- Add Constraints for Representations
ALTER TABLE representations
    ADD CONSTRAINT fk_representations_location_id FOREIGN KEY (location_id)
        REFERENCES locations (id) ON UPDATE CASCADE ON DELETE CASCADE;

-- Add Constraints for Shows
ALTER TABLE shows
    ADD CONSTRAINT fk_shows_location FOREIGN KEY (location_id)
        REFERENCES locations (id) ON UPDATE CASCADE ON DELETE CASCADE;

-- Add Constraints for Reservations
ALTER TABLE reservations
    ADD CONSTRAINT fk_reservations_user FOREIGN KEY (user_id)
        REFERENCES users (id) ON UPDATE CASCADE ON DELETE CASCADE;

-- Add Constraints for Role_User
ALTER TABLE role_user
    ADD CONSTRAINT fk_role_user_role FOREIGN KEY (role_id)
        REFERENCES roles (id) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE role_user
    ADD CONSTRAINT fk_role_user_user FOREIGN KEY (user_id)
        REFERENCES users (id) ON UPDATE CASCADE ON DELETE CASCADE;

-- Add Constraints for Reviews
ALTER TABLE reviews
    ADD CONSTRAINT fk_reviews_user FOREIGN KEY (user_id)
        REFERENCES users (id) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE reviews
    ADD CONSTRAINT fk_reviews_show FOREIGN KEY (show_id)
        REFERENCES shows (id) ON UPDATE CASCADE ON DELETE CASCADE;

-- Add Constraints for Locations (Locality)
ALTER TABLE locations
    ADD CONSTRAINT fk_locations_locality FOREIGN KEY (locality_id)
        REFERENCES localities (id) ON UPDATE CASCADE ON DELETE CASCADE;