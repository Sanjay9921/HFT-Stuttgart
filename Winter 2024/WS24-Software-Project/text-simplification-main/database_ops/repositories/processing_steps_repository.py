from database_ops.models.processing_step import ProcessingStep
from database_ops.utils.db import db

def add_processing_step(step_name, session):
    """
    Repository function to create a new processing step.
    """
    new_step = ProcessingStep(step_name=step_name)
    session.add(new_step)
    return new_step

def get_all_processing_steps():
    """
    Repository function to retrieve all processing steps.
    """
    return ProcessingStep.query.all()

def get_processing_step_by_id(step_id):
    """
    Retrieve a processing step by its ID.
    """
    return ProcessingStep.query.get(step_id)

def get_processing_step_by_name(step_name):
    """
    Retrieve a processing step by its name.
    """
    return ProcessingStep.query.filter_by(step_name=step_name).first()

def update_processing_step(step_id, step_name=None):
    """
    Update the name of a processing step by its ID.
    """
    step = get_processing_step_by_id(step_id)
    if step and step_name is not None:
        step.step_name = step_name
        db.session.commit()
    return step

def delete_processing_step(step_id):
    """
    Delete a processing step by its ID.
    """
    step = get_processing_step_by_id(step_id)
    if step:
        db.session.delete(step)
        db.session.commit()
    return step