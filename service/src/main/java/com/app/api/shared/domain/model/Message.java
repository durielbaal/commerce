package com.app.api.shared.domain.model;

public record Message<B>(String address, B body) {

}
