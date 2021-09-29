package seedu.address.model.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.application.exceptions.DuplicatePersonException;
import seedu.address.model.application.exceptions.PersonNotFoundException;
import seedu.address.testutil.PersonBuilder;

public class UniqueApplicationListTest {

    private final UniqueApplicationList uniqueApplicationList = new UniqueApplicationList();

    @Test
    public void contains_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueApplicationList.contains(null));
    }

    @Test
    public void contains_personNotInList_returnsFalse() {
        assertFalse(uniqueApplicationList.contains(ALICE));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        uniqueApplicationList.add(ALICE);
        assertTrue(uniqueApplicationList.contains(ALICE));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        uniqueApplicationList.add(ALICE);
        Application editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniqueApplicationList.contains(editedAlice));
    }

    @Test
    public void add_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueApplicationList.add(null));
    }

    @Test
    public void add_duplicatePerson_throwsDuplicatePersonException() {
        uniqueApplicationList.add(ALICE);
        assertThrows(DuplicatePersonException.class, () -> uniqueApplicationList.add(ALICE));
    }

    @Test
    public void setPerson_nullTargetPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueApplicationList.setPerson(null, ALICE));
    }

    @Test
    public void setPerson_nullEditedPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueApplicationList.setPerson(ALICE, null));
    }

    @Test
    public void setPerson_targetPersonNotInList_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> uniqueApplicationList.setPerson(ALICE, ALICE));
    }

    @Test
    public void setPerson_editedPersonIsSamePerson_success() {
        uniqueApplicationList.add(ALICE);
        uniqueApplicationList.setPerson(ALICE, ALICE);
        UniqueApplicationList expectedUniqueApplicationList = new UniqueApplicationList();
        expectedUniqueApplicationList.add(ALICE);
        assertEquals(expectedUniqueApplicationList, uniqueApplicationList);
    }

    @Test
    public void setPerson_editedPersonHasSameIdentity_success() {
        uniqueApplicationList.add(ALICE);
        Application editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        uniqueApplicationList.setPerson(ALICE, editedAlice);
        UniqueApplicationList expectedUniqueApplicationList = new UniqueApplicationList();
        expectedUniqueApplicationList.add(editedAlice);
        assertEquals(expectedUniqueApplicationList, uniqueApplicationList);
    }

    @Test
    public void setPerson_editedPersonHasDifferentIdentity_success() {
        uniqueApplicationList.add(ALICE);
        uniqueApplicationList.setPerson(ALICE, BOB);
        UniqueApplicationList expectedUniqueApplicationList = new UniqueApplicationList();
        expectedUniqueApplicationList.add(BOB);
        assertEquals(expectedUniqueApplicationList, uniqueApplicationList);
    }

    @Test
    public void setPerson_editedPersonHasNonUniqueIdentity_throwsDuplicatePersonException() {
        uniqueApplicationList.add(ALICE);
        uniqueApplicationList.add(BOB);
        assertThrows(DuplicatePersonException.class, () -> uniqueApplicationList.setPerson(ALICE, BOB));
    }

    @Test
    public void remove_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueApplicationList.remove(null));
    }

    @Test
    public void remove_personDoesNotExist_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> uniqueApplicationList.remove(ALICE));
    }

    @Test
    public void remove_existingPerson_removesPerson() {
        uniqueApplicationList.add(ALICE);
        uniqueApplicationList.remove(ALICE);
        UniqueApplicationList expectedUniqueApplicationList = new UniqueApplicationList();
        assertEquals(expectedUniqueApplicationList, uniqueApplicationList);
    }

    @Test
    public void setPersons_nullUniquePersonList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueApplicationList.setPersons((UniqueApplicationList) null));
    }

    @Test
    public void setPersons_uniquePersonList_replacesOwnListWithProvidedUniquePersonList() {
        uniqueApplicationList.add(ALICE);
        UniqueApplicationList expectedUniqueApplicationList = new UniqueApplicationList();
        expectedUniqueApplicationList.add(BOB);
        uniqueApplicationList.setPersons(expectedUniqueApplicationList);
        assertEquals(expectedUniqueApplicationList, uniqueApplicationList);
    }

    @Test
    public void setPersons_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueApplicationList.setPersons((List<Application>) null));
    }

    @Test
    public void setPersons_list_replacesOwnListWithProvidedList() {
        uniqueApplicationList.add(ALICE);
        List<Application> applicationList = Collections.singletonList(BOB);
        uniqueApplicationList.setPersons(applicationList);
        UniqueApplicationList expectedUniqueApplicationList = new UniqueApplicationList();
        expectedUniqueApplicationList.add(BOB);
        assertEquals(expectedUniqueApplicationList, uniqueApplicationList);
    }

    @Test
    public void setPersons_listWithDuplicatePersons_throwsDuplicatePersonException() {
        List<Application> listWithDuplicateApplications = Arrays.asList(ALICE, ALICE);
        assertThrows(DuplicatePersonException.class, () -> uniqueApplicationList.setPersons(listWithDuplicateApplications));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueApplicationList.asUnmodifiableObservableList().remove(0));
    }
}
