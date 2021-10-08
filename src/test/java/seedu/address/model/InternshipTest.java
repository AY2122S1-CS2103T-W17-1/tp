package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_AMAZON;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalApplications.AMAZON;
import static seedu.address.testutil.TypicalApplications.getTypicalInternship;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.application.Application;
import seedu.address.model.application.exceptions.DuplicateApplicationException;
import seedu.address.testutil.ApplicationBuilder;

public class InternshipTest {

    private final Internship addressBook = new Internship();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getApplicationList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyInternship_replacesData() {
        Internship newData = getTypicalInternship();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicateApplications_throwsDuplicateApplicationException() {
        // Two applications with the same identity fields
        Application editedAlice = new ApplicationBuilder(AMAZON).withTags(VALID_TAG_AMAZON)
                .build();
        List<Application> newApplications = Arrays.asList(AMAZON, editedAlice);
        InternshipStub newData = new InternshipStub(newApplications);

        assertThrows(DuplicateApplicationException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void hasApplication_nullApplication_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasApplication(null));
    }

    @Test
    public void hasApplication_applicationNotInInternship_returnsFalse() {
        assertFalse(addressBook.hasApplication(AMAZON));
    }

    @Test
    public void hasApplication_applicationInInternship_returnsTrue() {
        addressBook.addApplication(AMAZON);
        assertTrue(addressBook.hasApplication(AMAZON));
    }

    @Test
    public void hasApplication_applicationWithSameIdentityFieldsInInternship_returnsTrue() {
        addressBook.addApplication(AMAZON);
        Application editedAlice = new ApplicationBuilder(AMAZON).withTags(VALID_TAG_AMAZON)
                .build();
        assertTrue(addressBook.hasApplication(editedAlice));
    }

    @Test
    public void getApplicationList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getApplicationList().remove(0));
    }

    /**
     * A stub ReadOnlyInternship whose applications list can violate interface constraints.
     */
    private static class InternshipStub implements ReadOnlyInternship {
        private final ObservableList<Application> applications = FXCollections.observableArrayList();

        InternshipStub(Collection<Application> applications) {
            this.applications.setAll(applications);
        }

        @Override
        public ObservableList<Application> getApplicationList() {
            return applications;
        }
    }

}
