package org.javers.core.commit

import org.javers.core.diff.Change
import org.javers.core.diff.DiffAssert
import org.javers.core.metamodel.object.GlobalCdoId
import org.javers.core.snapshot.SnapshotsAssert

/**
 * @author bartosz walacik
 */
class CommitAssert {
    Commit actual
    DiffAssert diffAssert
    SnapshotsAssert snapshotsAssert

    private CommitAssert(Commit actual){
        this.actual = actual
        this.diffAssert = DiffAssert.assertThat(actual.diff)
        this.snapshotsAssert = SnapshotsAssert.assertThat(actual.snapshots)
    }

    static assertThat = { Commit actual ->
        new CommitAssert(actual)
    }

    CommitAssert hasSnapshots(int expectedCount){
        snapshotsAssert.hasSize(expectedCount)
        this
    }

    CommitAssert hasSnapshot(GlobalCdoId.GlobalCdoIdDTO expectedId){
        assert snapshotsAssert.hasSnapshot(expectedId)
        this
    }

    CommitAssert hasChanges(int expectedCount){
        diffAssert.hasChanges(expectedCount)
        this
    }

    CommitAssert hasId(String expected){
        assert actual.id.value() == expected
        assert actual.snapshots.each {assert it.commitId == actual.id}
        this
    }

    CommitAssert hasChanges(int expectedSize, Class<? extends Change> expectedClass) {
        diffAssert.has(expectedSize, expectedClass)
        this
    }

    CommitAssert hasNewObject(def expectedId, Map<String, Object> expectedInitial){
        diffAssert.hasNewObject(expectedId, expectedInitial)
        this
    }

    CommitAssert hasObjectRemoved(def expectedId){
        diffAssert.hasObjectRemoved(expectedId)
        this
    }

    CommitAssert hasValueChangeAt(String property, Object oldVal, Object newVal) {
        diffAssert.hasValueChangeAt(property,oldVal,newVal)
        this
    }

    CommitAssert hasReferenceChangeAt(String property, def oldRef, def newRef) {
        diffAssert.hasReferenceChangeAt(property,oldRef,newRef)
        this
    }

    CommitAssert hasSnapshot(def expectedSnapshotId, Map<String, Object> expectedState){
        snapshotsAssert.hasSnapshot(expectedSnapshotId, expectedState)
        this
    }
}