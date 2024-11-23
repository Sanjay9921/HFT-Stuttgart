from database_ops.repositories.processing_steps_repository import add_processing_step, get_all_processing_steps, get_processing_step_by_id, delete_processing_step
from database_ops.models.processing_step import ProcessingStep

def test_add_processing_step(session):
    step = add_processing_step(step_name="Proofreading")
    assert step.step_id is not None
    assert step.step_name == "Proofreading"

def test_get_all_processing_steps(session):
    add_processing_step(step_name="Editing")
    add_processing_step(step_name="Publishing")
    
    steps = get_all_processing_steps()
    assert len(steps) == 2
    assert steps[0].step_name == "Editing"
    assert steps[1].step_name == "Publishing"

def test_get_processing_step_by_id(session):
    step = add_processing_step(step_name="Review")
    fetched_step = get_processing_step_by_id(step.step_id)
    
    assert fetched_step is not None
    assert fetched_step.step_name == "Review"

def test_delete_processing_step(session):
    step = add_processing_step(step_name="Obsolete Step")
    
    # Call delete_processing_step, which will return the deleted step object
    deleted_step = delete_processing_step(step.step_id)

    # Check if the deleted_step is the same as the one we created
    assert deleted_step is not None  # Ensure that the step is deleted (not None)
    assert deleted_step.step_name == step.step_name  # Ensure the deleted step matches the original one

    # Verify that the step is indeed deleted from the database
    assert ProcessingStep.query.get(step.step_id) is None  # Ensure the step is no longer in the database

