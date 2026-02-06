1. What happens when job is created?
   •	job row is saved
   •	outbox row is saved
   •	both in one DB transaction

⸻

2. Why not publish directly?
   •	because DB + Kafka cannot be one transaction

⸻

3. What is in outbox table?
   •	event metadata + JSON payload

⸻

4. Who sends to Kafka?
   •	your own background publisher (OutboxPublisher)

⸻

5. What if Kafka is down?
   •	job is still created
   •	event waits in DB
   •	will be retried later