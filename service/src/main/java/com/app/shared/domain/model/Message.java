package com.app.shared.domain.model;

public record Message<B>(String address, B body) {

}
