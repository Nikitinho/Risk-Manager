package com.nikitinho.riskmanager.domain;

public final class Views {
    public interface Id {}

    public interface IdName extends Id {}

    public interface FullProject extends IdName {}

    public interface FullRisk extends IdName {}

    public interface FullRiskComment extends IdName {}

    public interface FullBoard extends IdName {}

    public interface FullBoardItem extends IdName {}
}
