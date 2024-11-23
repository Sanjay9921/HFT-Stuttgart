from database_ops.utils.db import db
class ProcessingStep(db.Model):
    __tablename__ = 'ProcessingSteps'

    step_id = db.Column(db.Integer, primary_key=True)
    step_name = db.Column(db.String(50), nullable=False)

    def as_dict(self):
        return {
            'step_id': self.step_id,
            'step_name': self.step_name
        }