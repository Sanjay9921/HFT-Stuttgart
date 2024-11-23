from flask import Blueprint, request, jsonify
from database_ops.models.book_versions import BookVersion
from database_ops.utils.db import db
from database_ops.repositories.book_versions_repository import *  # Import repository functions

book_versions_bp = Blueprint('book_versions', __name__)

@book_versions_bp.route('/', methods=['POST'])
def create_book_version_route():
    data = request.get_json()
    new_version = add_book_version(data['book_id'], data['step_id'], data['content'])
    return jsonify({"message": "Book version created successfully", "version": new_version.as_dict()}), 201

@book_versions_bp.route('/<int:book_id>', methods=['GET'])
def get_book_versions_route(book_id):
    versions = get_versions_by_book_id(book_id)
    return jsonify([version.as_dict() for version in versions])

@book_versions_bp.route('/<int:version_id>', methods=['GET'])
def get_version_by_id_route(version_id):
    version = get_version_by_id(version_id)
    if version:
        return jsonify(version.as_dict())
    return jsonify({"error": "Version not found"}), 404

@book_versions_bp.route('/<int:version_id>', methods=['PUT'])
def update_book_version_route(version_id):
    data = request.get_json()
    updated_version = update_book_version(version_id, data.get('content'))
    if updated_version:
        return jsonify({"message": "Book version updated successfully", "version": updated_version.as_dict()})
    return jsonify({"error": "Version not found"}), 404

@book_versions_bp.route('/<int:version_id>', methods=['DELETE'])
def delete_book_version_route(version_id):
    deleted_version = delete_book_version(version_id)
    if deleted_version:
        return jsonify({"message": "Book version deleted successfully"})
    return jsonify({"error": "Version not found"}), 404