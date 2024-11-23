from flask import Blueprint, request, jsonify
from database_ops.models.processing_step import ProcessingStep
from database_ops.repositories.processing_steps_repository import *  # Import repository functions

processing_steps_bp = Blueprint('processing_steps', __name__)

@processing_steps_bp.route('/', methods=['POST'])
def create_processing_step_route():
    data = request.get_json()
    new_step = add_processing_step(data['step_name'])
    return jsonify({"message": "Processing step created successfully", "step": new_step.as_dict()}), 201

@processing_steps_bp.route('/', methods=['GET'])
def get_processing_steps_route():
    steps = get_all_processing_steps()
    return jsonify([step.as_dict() for step in steps])

@processing_steps_bp.route('/<int:step_id>', methods=['GET'])
def get_processing_step_by_id_route(step_id):
    step = get_processing_step_by_id(step_id)
    if step:
        return jsonify(step.as_dict())
    return jsonify({"error": "Processing step not found"}), 404

@processing_steps_bp.route('/name/<string:step_name>', methods=['GET'])
def get_processing_step_by_name_route(step_name):
    step = get_processing_step_by_name(step_name)
    if step:
        return jsonify(step.as_dict())
    return jsonify({"error": "Processing step not found"}), 404

@processing_steps_bp.route('/<int:step_id>', methods=['PUT'])
def update_processing_step_route(step_id):
    data = request.get_json()
    updated_step = update_processing_step(step_id, data.get('step_name'))
    if updated_step:
        return jsonify({"message": "Processing step updated successfully", "step": updated_step.as_dict()})
    return jsonify({"error": "Processing step not found"}), 404

@processing_steps_bp.route('/<int:step_id>', methods=['DELETE'])
def delete_processing_step_route(step_id):
    deleted_step = delete_processing_step(step_id)
    if deleted_step:
        return jsonify({"message": "Processing step deleted successfully"})
    return jsonify({"error": "Processing step not found"}), 404
