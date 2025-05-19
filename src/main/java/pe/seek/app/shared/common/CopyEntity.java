package pe.seek.app.shared.common;

public interface CopyEntity<ENTITY> {
    ENTITY copyFrom(ENTITY item);
}
