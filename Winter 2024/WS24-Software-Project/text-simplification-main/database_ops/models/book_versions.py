from database_ops.utils.db import db
from sqlalchemy.dialects.mysql import LONGTEXT

class BookVersion(db.Model):
    __tablename__ = 'BookVersions'

    version_id = db.Column(db.Integer, primary_key=True)
    book_id = db.Column(db.Integer, db.ForeignKey('Books.book_id', ondelete='CASCADE'), nullable=False)
    step_id = db.Column(db.Integer, db.ForeignKey('ProcessingSteps.step_id', ondelete='CASCADE'), nullable=False)
    content = db.Column(LONGTEXT, nullable=False)
    created_at = db.Column(db.DateTime, server_default=db.func.now())

    def as_dict(self):
        return {
            'version_id': self.version_id,
            'book_id': self.book_id,
            'step_id': self.step_id,
            'content': self.content,
            'created_at': self.created_at.isoformat()  # Convert datetime to string
        }